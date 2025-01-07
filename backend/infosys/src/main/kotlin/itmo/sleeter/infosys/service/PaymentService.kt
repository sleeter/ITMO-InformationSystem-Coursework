package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.model.Payment
import itmo.sleeter.infosys.repository.PaymentRepository
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository
) {
    fun getPayment(id: Long): Payment {
        return paymentRepository.findById(id).get()
    }
}