package com.erich.management.Services.Impl;

import com.erich.management.Dto.ClientDto;
import com.erich.management.Entity.ClientOrder;
import com.erich.management.Exception.EntityNotFoundException;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidEntityException;
import com.erich.management.Exception.InvalidOperationException;
import com.erich.management.Repository.ClientOrderRepository;
import com.erich.management.Repository.ClientRepository;
import com.erich.management.Services.ClientService;
import com.erich.management.Validators.ClientValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepo;
    private final ClientOrderRepository clientOrderRepo;

    @Override
    public ClientDto save(ClientDto clientDto) {
        List<String> erros = ClientValidator.validator(clientDto);
        if (!erros.isEmpty()) {
            throw new InvalidEntityException("The client is invalid", ErrorCodes.CLIENT_NOT_VALID, erros);
        }

        return ClientDto.fromEntity(clientRepo.save(ClientDto.toEntity(clientDto)));
    }

    @Override
    public ClientDto findById(Long id) {

        return clientRepo.findById(id).filter(idClien -> idClien.getId() != null)
                .map(client -> ClientDto.fromEntity(client))
                .orElseThrow(() -> new EntityNotFoundException("Client not exist with" + id + "in bd", ErrorCodes.CLIENT_NOT_FOUND));
    }

    @Override
    public List<ClientDto> findAll() {
        return Streamable.of(clientRepo.findAll())
                .map(client -> ClientDto.fromEntity(client))
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            log.error("Client ID is null");
            return;
        }
        List<ClientOrder> clientOrders = clientOrderRepo.findAllByClientId(id);
        if (!clientOrders.isEmpty()) {
            throw new InvalidOperationException("Can't delete a customer who already has sales orders", ErrorCodes.CLIENT_ALREADY_IN_USE);
        }
        clientRepo.deleteById(id);
    }
}
