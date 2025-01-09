package itmo.sleeter.infosys.mapper

import itmo.sleeter.infosys.dto.response.PaymentResponse
import itmo.sleeter.infosys.model.Payment
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component
interface PaymentMapper {
    fun paymentToPaymentResponse(payment: Payment): PaymentResponse
}