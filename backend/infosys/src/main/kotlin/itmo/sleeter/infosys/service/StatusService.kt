package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.repository.StatusRepository
import org.springframework.stereotype.Service

@Service
class StatusService(
    private val statusRepository: StatusRepository
) {
}