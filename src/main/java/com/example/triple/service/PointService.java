package com.example.triple.service;

import com.example.triple.domain.point.PointRepository;
import com.example.triple.dto.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
public class PointService {

    @Autowired
    PointRepository pointRepository;

    public boolean DispatcherType(EventDto dto){
        switch (dto.getAction()){
            case "ADD" :
                add(dto);
                break;
            case "MOD" :
                mod(dto);
                break;
            case "DELETE" :
                delete(dto);
                break;
            default: return false;
        }
        return true;
    }

    private void delete(EventDto dto) {

    }

    private void mod(EventDto dto) {

    }

    private void add(EventDto dto) {
        pointRepository.save(dto);

    }
}
