package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    val customerRepository: CustomerRepository
) {
}