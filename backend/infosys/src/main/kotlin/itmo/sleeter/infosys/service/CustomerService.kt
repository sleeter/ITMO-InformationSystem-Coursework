package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.dto.response.CustomerResponse
import itmo.sleeter.infosys.mapper.CustomerMapper
import itmo.sleeter.infosys.model.Customer
import itmo.sleeter.infosys.repository.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val customerMapper: CustomerMapper
) {
    fun getCustomer(id: Long): Customer {
        return customerRepository.findById(id).get()
    }
    fun getCustomers(pageable: Pageable): Page<CustomerResponse> {
        val page = customerRepository.findAll(pageable).map { customerMapper.customerToCustomerResponse(it) }
        return PageImpl(page.content, pageable, page.totalElements)
    }
}