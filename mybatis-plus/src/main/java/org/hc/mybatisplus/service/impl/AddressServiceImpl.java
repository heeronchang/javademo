package org.hc.mybatisplus.service.impl;

import org.hc.mybatisplus.domain.po.Address;
import org.hc.mybatisplus.mapper.AddressMapper;
import org.hc.mybatisplus.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *
 * @author heeron
 * @since 2024-11-23
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
