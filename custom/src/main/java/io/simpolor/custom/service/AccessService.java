package io.simpolor.custom.service;

import io.simpolor.custom.repository.AccessRepository;
import io.simpolor.custom.repository.entity.Access;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessService {

	private final AccessRepository accessRepository;

	public List<Access> getAccessList() {
		return accessRepository.findAll();
	}

}
