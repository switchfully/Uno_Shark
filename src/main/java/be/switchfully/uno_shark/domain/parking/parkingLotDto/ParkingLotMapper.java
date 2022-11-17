package be.switchfully.uno_shark.domain.parking.parkingLotDto;

import be.switchfully.uno_shark.domain.parking.Parkinglot;
import be.switchfully.uno_shark.domain.parking.divisionDto.DivisionMapper;
import be.switchfully.uno_shark.domain.person.dto.PersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParkingLotMapper {
    Logger log = LoggerFactory.getLogger(getClass());
    private final DivisionMapper divisionMapper;
    private final PersonMapper personMapper;
    private ParkingLotMapper(DivisionMapper divisionMapper, PersonMapper personMapper) {
        this.divisionMapper = divisionMapper;
        this.personMapper = personMapper;
    }

    public Parkinglot dtoToParkingLot(CreateParkingLotDto createParkingLotDto) {
        //todo fix
        return null;
    }

    public Parkinglot CreateDtoToParkingLot(CreateParkingLotDto createParkingLotDto) {
        log.info("Converting Dto -> Parkinglot");
        return new Parkinglot(
                createParkingLotDto.getName(),
                createParkingLotDto.getParkingCategory(),
                divisionMapper.mapToDivision(createParkingLotDto.getDivision()),
                createParkingLotDto.getCapacity(),
                personMapper.dtoToPerson(createParkingLotDto.getPerson()),
                createParkingLotDto.getAddress(),
                createParkingLotDto.getPricePerHour()
        );
    }

    public ParkingLotDto parkingLotToDto(Parkinglot parkinglot) {
        log.info("Converting Dto <- Parkinglot");
        //Todo change Dtos
        return new ParkingLotDto(
                parkinglot.getId(),
                parkinglot.getName(),
                parkinglot.getParkingCategory(),
                divisionMapper.mapSingleDivisionDto(parkinglot.getDivision()),
                parkinglot.getCapacity(),
                parkinglot.getPerson(),
                parkinglot.getAddress(),
                parkinglot.getPricePerHour()
        );
    }

    public List<ParkingLotDto> parkingLotListToDto(List<Parkinglot> parkingLots) {
        return parkingLots.stream().map(this::parkingLotToDto).toList();
    }
}
