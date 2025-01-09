package itmo.sleeter.infosys.mapper

import itmo.sleeter.infosys.dto.response.CustomerResponse
import itmo.sleeter.infosys.model.Customer
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component
interface CustomerMapper {
    fun customerToCustomerResponse(customer: Customer): CustomerResponse
}