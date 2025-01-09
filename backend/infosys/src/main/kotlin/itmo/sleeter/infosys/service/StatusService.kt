package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.dto.response.StatusResponse
import itmo.sleeter.infosys.mapper.StatusMapper
import itmo.sleeter.infosys.model.Status
import itmo.sleeter.infosys.repository.StatusRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class StatusService(
    private val statusRepository: StatusRepository,
    private val statusMapper: StatusMapper
) {
    fun getStatus(statusId: Long): Status {
        return statusRepository.findById(statusId).get()
    }
    fun getStatuses(): List<StatusResponse> {
        val res = statusRepository.findAll().map { statusMapper.statusToStatusResponse(it) }
        return res
    }
}