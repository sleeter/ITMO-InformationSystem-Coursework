package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.dto.response.PaymentResponse
import itmo.sleeter.infosys.mapper.PaymentMapper
import itmo.sleeter.infosys.model.Payment
import itmo.sleeter.infosys.repository.PaymentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val paymentMapper: PaymentMapper
) {
    fun getPayment(id: Long): Payment {
        return paymentRepository.findById(id).get()
    }
    fun getPayments(pageable: Pageable): Page<PaymentResponse> {
        val page = paymentRepository.findAll(pageable).map { paymentMapper.paymentToPaymentResponse(it) }
        return PageImpl(page.content, pageable, page.totalElements)
    }
}