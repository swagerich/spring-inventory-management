package com.erich.management.Services.Impl;

import com.erich.management.Dto.*;
import com.erich.management.Entity.Article;
import com.erich.management.Entity.Client;
import com.erich.management.Entity.ClientOrder;
import com.erich.management.Entity.ClientOrderLine;
import com.erich.management.Exception.EntityNotFoundException;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidEntityException;
import com.erich.management.Exception.InvalidOperationException;
import com.erich.management.Repository.ArticleRepository;
import com.erich.management.Repository.ClientOrderLineRepository;
import com.erich.management.Repository.ClientOrderRepository;
import com.erich.management.Repository.ClientRepository;
import com.erich.management.Services.ClientOrderService;
import com.erich.management.Services.StockService;
import com.erich.management.Utils.Enum.OrdeStatus;
import com.erich.management.Utils.Enum.SourceStock;
import com.erich.management.Utils.Enum.TypeStock;
import com.erich.management.Validators.ArticleValidator;
import com.erich.management.Validators.ClientOrdeValidator;
import com.erich.management.Validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClientOrderServiceImpl implements ClientOrderService {

    private final ClientOrderRepository clientOrderRepo;
    private final ClientOrderLineRepository clientOrderLineRepo;
    private final ClientRepository clientRepo;
    private final ArticleRepository articleRepo;
    private final StockService stockService;


    @Override
    public ClientOrderDto save(ClientOrderDto clientOrderDto) {

        List<String> errors = ClientOrdeValidator.validate(clientOrderDto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("the client's request is not valid", ErrorCodes.CLIENT_ORDER_NOT_VALID, errors);
        }

        Optional<Client> client = clientRepo.findById(clientOrderDto.getClient().getId());
        if (client.isEmpty()) {
            throw new InvalidEntityException("No customer with ID in the bd", ErrorCodes.CLIENT_NOT_FOUND);
        }


        List<String> articleError = new ArrayList<>();

        if (clientOrderDto.getClientOrderLine() != null) {

            clientOrderDto.getClientOrderLine().forEach(clientOrderLineDto -> {
                if (clientOrderLineDto.getArticle() != null) {
                    Optional<Article> article = articleRepo.findById(clientOrderLineDto.getArticle().getId());
                    if (article.isEmpty()) {
                        articleError.add("item with id does not exist");
                    }
                }
            });
        }
        if (!articleError.isEmpty()) {
            throw new InvalidEntityException("No item with in the bd", ErrorCodes.ARTICLE_NOT_FOUND, articleError);
        }

        ClientOrder saveClientOrder = clientOrderRepo.save(ClientOrderDto.toEntity(clientOrderDto));

        if (clientOrderDto.getClientOrderLine() != null) {

            clientOrderDto.getClientOrderLine().forEach(line -> {
                ClientOrderLine clientOrderLine1 = ClientOrderLineDto.toEntity(line);
                clientOrderLine1.setClientOrder(saveClientOrder);
                clientOrderLineRepo.save(clientOrderLine1);
            });
        }

        return ClientOrderDto.fromEntity(saveClientOrder);
    }

    @Override
    public ClientOrderDto updateOrdeStatus(Long idOrder, OrdeStatus ordeStatus) {
        checkoutOrderStatus(idOrder);
        if (!StringUtils.hasLength(String.valueOf(ordeStatus))) {
            throw new InvalidOperationException("Cannot change order status with null status",
                    ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
        }
        ClientOrderDto clientOrderDto = checkoutOrderStatus(idOrder);
        clientOrderDto.setOrdeStatus(ordeStatus);

        ClientOrder saveClientOrder = clientOrderRepo.save(ClientOrderDto.toEntity(clientOrderDto));
        if (clientOrderDto.isOrderDelivered()) {
            updateStock(idOrder);
        }
        return ClientOrderDto.fromEntity(saveClientOrder);
    }

    @Override
    public ClientOrderDto updateQuantityOrde(Long idOrder, Long idLineOrder, BigDecimal quantity) {
        checkoutIdOrder(idOrder);
        checkoutIdOrderLine(idLineOrder);
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) == 0) {
            throw new InvalidOperationException("The status of the order cannot be changed with null or ZERO quantity",
                    ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
        }
        ClientOrderDto clientOrderDto = checkoutOrderStatus(idOrder);
        Optional<ClientOrderLine> clientOrderLine = findClientOrderLine(idLineOrder);

        if (clientOrderLine.isPresent()) {

            ClientOrderLine line = clientOrderLine.get();
            line.setQuantity(quantity);
            clientOrderLineRepo.save(line);
        }

        return clientOrderDto;
    }

    @Override
    public ClientOrderDto updateClient(Long idOrder, Long idClient) {
        checkoutIdOrder(idOrder);
        if (idClient == null) {
            throw new InvalidOperationException("Cannot change order status with null customer id", ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
        }
        ClientOrderDto clientOrder = checkoutOrderStatus(idOrder);
        Optional<Client> client = clientRepo.findById(idClient);
        if (client.isEmpty()) {
            throw new EntityNotFoundException(
                    "No customer with ID found" + idClient, ErrorCodes.CLIENT_NOT_FOUND);
        }
        clientOrder.setClient(ClientDto.fromEntity(client.get()));

        return ClientOrderDto.fromEntity(clientOrderRepo.save(ClientOrderDto.toEntity(clientOrder)));
    }

    @Override
    public ClientOrderDto updateArticle(Long idOrder, Long idLineOrder, Long idArticle) {
        checkoutIdOrder(idOrder);
        checkoutIdOrderLine(idLineOrder);
        checkoutArticle(idArticle, "new");

        ClientOrderDto clientOrderDto = checkoutOrderStatus(idOrder);
        Optional<ClientOrderLine> clientOrderLine = findClientOrderLine(idLineOrder);

        Optional<Article> article = articleRepo.findById(idArticle);
        if (article.isEmpty()) {
            throw new EntityNotFoundException("No article found with the ID" + idArticle, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(article.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article Invalid", ErrorCodes.ARTICLE_NOT_VALID);
        }

        if (clientOrderLine.isPresent()) {
            ClientOrderLine line = clientOrderLine.get();

            line.setArticle(article.get());
            clientOrderLineRepo.save(line);
        }


        return clientOrderDto;
    }

    @Override
    public ClientOrderDto deleteArticle(Long idOrder, Long idOrderLine) {
        checkoutIdOrder(idOrder);
        checkoutIdOrderLine(idOrderLine);

        //Solo para verificar CustomerOrderLine e informar al cliente en caso de que esté ausente
        ClientOrderDto clientOrderDto = checkoutOrderStatus(idOrder);
        findClientOrderLine(idOrderLine);
        clientOrderLineRepo.deleteById(idOrderLine);
        return clientOrderDto;
    }

    @Override
    public ClientOrderDto findById(Long id) {

        return clientOrderRepo.findById(id)
                .filter(ids -> ids.getId() != null)
                .map(clientOrder -> ClientOrderDto.fromEntity(clientOrder))
                .orElseThrow(() -> new EntityNotFoundException("No sales order with code found" + id, ErrorCodes.CLIENT_ORDER_NOT_FOUND));
    }

    @Override
    public ClientOrderDto findClientByCode(String code) {
        return clientOrderRepo.findClientOrderByCode(code)
                .filter(cod -> cod.getId() != null)
                .map(clientOrder -> ClientOrderDto.fromEntity(clientOrder))
                .orElseThrow(() -> new EntityNotFoundException("No sales order with code found" + code, ErrorCodes.CLIENT_ORDER_NOT_FOUND));
    }

    @Override
    public List<ClientOrderDto> findAll() {
        return Streamable.of(clientOrderRepo.findAll()).stream()
                .map(clientOrder -> ClientOrderDto.fromEntity(clientOrder))
                .toList();
    }

    @Override
    public List<ClientOrderLineDto> findAllClientOrderLineByClientOrderId(Long idOrder) {

        return clientOrderLineRepo.findAllByClientOrderId(idOrder)
                .stream()
                .map(clientOrderLine -> ClientOrderLineDto.fromEntity(clientOrderLine))
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            return;
        }
        List<ClientOrderLine> orderLines = clientOrderLineRepo.findAllByClientOrderId(id);
        if (!orderLines.isEmpty()) {
            throw new InvalidOperationException("Cannot delete an already used sales order", ErrorCodes.CLIENT_ORDER_ALREADY_IN_USE);
        }
        clientOrderRepo.deleteById(id);
    }

    private Optional<ClientOrderLine> findClientOrderLine(Long idLineOrder) {
        Optional<ClientOrderLine> clientOrderLine = clientOrderLineRepo.findById(idLineOrder);
        if (clientOrderLine.isEmpty()) {
            throw new EntityNotFoundException("No sales order line with ID found" + idLineOrder, ErrorCodes.CLIENT_ORDER_NOT_FOUND);
        }
        return clientOrderLine;
    }

    private void checkoutIdOrder(Long idOrder) {
        if (idOrder == null) {
            throw new InvalidOperationException("Cannot change order status with null ID", ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
        }
    }

    private void checkoutIdOrderLine(Long idLine) {
        if (idLine == null) {
            throw new InvalidOperationException("Cannot change order status with null command line", ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
        }
    }

    private void checkoutArticle(Long idArticle, String msg) {
        if (idArticle == null) {
            throw new InvalidOperationException("You cannot change the order status with a" + msg + "Id article null",
                    ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
        }
    }

    private ClientOrderDto checkoutOrderStatus(Long id) {
        ClientOrderDto clientOrderDto = findById(id);
        if (clientOrderDto.isOrderDelivered()) {
            throw new InvalidOperationException("Cannot change the order when it is delivered", ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
        }
        return clientOrderDto;
    }

    private void updateStock(Long id) {
        List<ClientOrderLine> lineList = clientOrderLineRepo.findAllByClientOrderId(id);
        lineList.forEach(this::makeExit);
    }

    private void makeExit(ClientOrderLine clientOrderLine) {
        StockDto stockDto = StockDto.builder()
                .article(ArticleDto.fromEntity(clientOrderLine.getArticle()))
                .dateStock(Instant.now())
                .typeStock(TypeStock.SORTIE)
                .sourceStock(SourceStock.ORDE_CLIENT)
                .quantity(clientOrderLine.getQuantity())
                .idEntreprise(clientOrderLine.getIdEntreprise())
                .build();
        stockService.stockSortie(stockDto);
    }
}
