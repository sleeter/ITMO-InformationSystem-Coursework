package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.dto.request.OrderRequest
import itmo.sleeter.infosys.dto.response.OrderResponse
import itmo.sleeter.infosys.dto.response.OrderedProductResponse
import itmo.sleeter.infosys.mapper.CustomerMapper
import itmo.sleeter.infosys.mapper.OrderMapper
import itmo.sleeter.infosys.mapper.PickupPointMapper
import itmo.sleeter.infosys.mapper.ProductMapper
import itmo.sleeter.infosys.model.Order
import itmo.sleeter.infosys.repository.OrderRepository
import org.apache.coyote.BadRequestException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.lang.Long.valueOf
import java.time.Instant

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productService: ProductService,
    private val pickupPointService: PickupPointService,
    private val statusService: StatusService,
    private val customerService: CustomerService,
    private val paymentService: PaymentService,
    private val orderMapper: OrderMapper,
    private val productMapper: ProductMapper,
    private val customerMapper: CustomerMapper,
    private val pickupPointMapper: PickupPointMapper,
    private val orderedProductService: OrderedProductService
) {
    fun createOrder(req: OrderRequest): OrderResponse {
        val customer = customerService.getCustomer(req.customerId)
        val status = statusService.getStatus(valueOf(0))
        val pickUpPoint = pickupPointService.getPickupPointById(req.pickupPointId)
        val payment = paymentService.getPayment(req.paymentId)
        val order = Order()
        order.customers = customer
        order.size = req.size
        order.date = Instant.now()
        order.status = status
        order.pickupPoint = pickUpPoint
        order.totalPrice = req.totalPrice
        order.payments = payment
        val savedOrder = orderRepository.save(order)
        val opr = mutableListOf<OrderedProductResponse>()
        req.products.forEach { productReq ->
            val product = productService.getProduct(productReq.productId)
            orderedProductService.saveOrderedProduct(product, order, productReq.count)
            opr.add(productMapper.productToOrderedProductResponse(productMapper.productToProductResponse(product), productReq.count))
        }
        return orderMapper.toOrderResponse(
            savedOrder,
            customerMapper.customerToCustomerResponse(savedOrder.customers!!),
            pickupPointMapper.pickupPointToPickupPointResponse(savedOrder.pickupPoint!!),
            opr
        )
    }
    fun updateOrderStatus(id: Long, statusId: Long) {
        val order = orderRepository.findById(id).get()
        if (statusId < order.status?.id!!) {
            throw BadRequestException("status is less than previous")
        }
        val status = statusService.getStatus(statusId)
        order.status = status
        orderRepository.save(order)
    }
    fun getOrders(pageable: Pageable): Page<OrderResponse> {
        val page = orderRepository.findAll(pageable).map { order ->
            val opr = mutableListOf<OrderedProductResponse>()
            order.orderedProducts.forEach { product ->
                opr.add(productMapper.productToOrderedProductResponse(productMapper.productToProductResponse(product.product!!), product.count!!))
            }
            orderMapper.toOrderResponse(
                order,
                customerMapper.customerToCustomerResponse(order.customers!!),
                pickupPointMapper.pickupPointToPickupPointResponse(order.pickupPoint!!),
                opr
            )
        }
        return PageImpl(page.content, pageable, page.totalElements)
    }
}