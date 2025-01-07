package itmo.sleeter.infosys.mapper

import itmo.sleeter.infosys.dto.response.CustomerResponse
import itmo.sleeter.infosys.dto.response.OrderResponse
import itmo.sleeter.infosys.dto.response.OrderedProductResponse
import itmo.sleeter.infosys.dto.response.PickupPointResponse
import itmo.sleeter.infosys.model.Order
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.stereotype.Component

@Mapper
@Component
interface OrderMapper {
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "status", source = "order.status.name")
    @Mapping(target = "payment", source = "order.payments.paymentsMethod")
    @Mapping(target = "pickupPoint", source = "pickupPoint")
    @Mapping(target = "size", source = "order.size")
    fun toOrderResponse(order: Order, customer: CustomerResponse, pickupPoint: PickupPointResponse, products: List<OrderedProductResponse>): OrderResponse
}