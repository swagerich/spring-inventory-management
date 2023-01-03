package com.erich.management.Services;

import com.erich.management.Dto.ClientDto;

import java.util.List;

public interface ClientService {

    ClientDto save(ClientDto clientDto);

    ClientDto findById(Long id);

    List<ClientDto> findAll();

    void delete(Long id);
}
