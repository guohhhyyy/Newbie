package com.example.controller;

import com.example.dto.OrderDTO;
import com.example.entity.Order;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500")  // 允许前端端口访问
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/save_order")
    public Map<String, Object> saveOrder(@RequestBody OrderDTO orderDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("收到订单数据：" + orderDTO);  // 调试日志

            // 生成订单号
            String orderNo = "ORD" + System.currentTimeMillis() +
                    UUID.randomUUID().toString().substring(0, 6).toUpperCase();

            // 创建订单实体
            Order order = new Order();
            order.setOrderNo(orderNo);
            order.setProductName(orderDTO.getProductName());
            order.setProductPrice(orderDTO.getProductPrice());
            order.setProductImage(orderDTO.getProductImage());
            order.setBuyerName(orderDTO.getBuyerName());
            order.setBuyerPhone(orderDTO.getBuyerPhone());
            order.setAddress(orderDTO.getAddress());
            order.setLatitude(orderDTO.getLatitude());
            order.setLongitude(orderDTO.getLongitude());
            order.setCreateTime(LocalDateTime.now());

            // 保存订单
            Order savedOrder = orderRepository.save(order);

            response.put("success", true);
            response.put("message", "订单保存成功");
            response.put("order_id", savedOrder.getId());
            response.put("order_no", savedOrder.getOrderNo());

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "保存失败：" + e.getMessage());
        }

        return response;
    }
}