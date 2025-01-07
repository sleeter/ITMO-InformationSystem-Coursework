package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.model.Status
import itmo.sleeter.infosys.repository.StatusRepository
import org.springframework.stereotype.Service

@Service
class StatusService(
    private val statusRepository: StatusRepository
) {
    fun getStatus(statusId: Long): Status {
        return statusRepository.findById(statusId).get()
    }
}