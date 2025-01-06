package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.repository.PaymentRepository
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository
) {
}