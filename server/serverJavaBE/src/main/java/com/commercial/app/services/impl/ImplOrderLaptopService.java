package com.commercial.app.services.impl;

import com.commercial.app.domain.dtos.request.OrderLaptopRequestDto;
import com.commercial.app.domain.entites.Laptop;
import com.commercial.app.domain.entites.Order;
import com.commercial.app.domain.entites.OrderLaptop;
import com.commercial.app.domain.mapper.OrderLaptopMapper;
import com.commercial.app.repositories.LaptopRepository;
import com.commercial.app.repositories.OrderLaptopRepository;
import com.commercial.app.repositories.OrderRepository;
import com.commercial.app.services.OrderLaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImplOrderLaptopService implements OrderLaptopService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private LaptopRepository laptopRepository;

    @Autowired
    private OrderLaptopRepository orderLaptopRepository;
    @Autowired
    private OrderLaptopMapper orderLaptopMapper;

    @Override
    public OrderLaptopRequestDto createOrderLaptop(OrderLaptopRequestDto orderLaptopRequestDto) {
        OrderLaptop orderLaptop = new OrderLaptop();
        Laptop laptop = laptopRepository.findFirstByModelContaining(orderLaptopRequestDto.getModel());
        Order order = orderRepository.findById(orderLaptopRequestDto.getOrderId()).get();
        orderLaptop.setLaptop(laptop);
        orderLaptop.setOrder(order);
        orderLaptop.setQuantity(orderLaptopRequestDto.getQuantity());
        orderLaptop.setTotalPrice(orderLaptop.getQuantity()*laptop.getPrice());
        return orderLaptopMapper.mapToDto(orderLaptopRepository.save(orderLaptop));
    }
}