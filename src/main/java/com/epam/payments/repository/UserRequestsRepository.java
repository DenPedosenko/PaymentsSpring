package com.epam.payments.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.payments.model.Request;
import com.epam.payments.model.RequestStatus;
import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;

public interface UserRequestsRepository extends JpaRepository<Request, Integer> {
	public List<Request> findAllByStatus(RequestStatus status);
	public Optional<Request> findByUserAndAccountAndStatus(User user, UserAccount account, RequestStatus status);
}
