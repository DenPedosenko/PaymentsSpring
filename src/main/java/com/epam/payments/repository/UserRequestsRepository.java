package com.epam.payments.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.payments.model.Request;
import com.epam.payments.model.RequestStatus;

public interface UserRequestsRepository extends JpaRepository<Request, Integer> {
	public List<Request> findAllByStatus(RequestStatus status);
}
