package com.fontana.backend.dmxHandler.currentStateDTO.mapper;

import com.fontana.backend.devices.entity.Device;
import com.fontana.backend.dmxHandler.currentStateDTO.factory.DeviceDTOFactory;
import com.fontana.backend.devices.repository.DeviceRepository;
import com.fontana.backend.dmxHandler.currentStateDTO.CurrentStateDTO;
import com.fontana.backend.dmxHandler.currentStateDTO.service.CurrentStateDTOService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrentStateDTOMapper {

    private final DeviceRepository deviceRepository;
    private final CurrentStateDTOService currentStateDTOService;
    private final DeviceDTOFactory deviceDTOFactory;

    public CurrentStateDTO DMXtoCurrentStateDTO(byte[] DMXDataArray) {
        //TODO dodac blackliste adresow roznych kanalow do swiatel
        CurrentStateDTO currentStateDTO = new CurrentStateDTO();
        List<Integer> visitedAddresses = new ArrayList<>();
        for (int i = 0; i < DMXDataArray.length -3; i++) {

            Optional<Device> device = deviceRepository.findById(i);

            if (device.isPresent() && !visitedAddresses.contains(i)) {
                //jesli jest device to pobieram jaki to jest typ device
                //jesli to jest jet to wystarczy pobrac id i value z device
                //jesli to jest pompa to wystarczy pobrac id i value z device
                //jesli jest to led to pobieram idki z adressess z device i z kazdego tego idka pobieram value z DMXDataArray
                //jesli jest to light to pobieram idki z adressess z device i z kazdego tego idka pobieram value z DMXDataArray
                //w przypadku led i light addresy poszczegolnych kanalow musze dodac do blacklisty
                //przekazuje DMXData dlatego ze device by default nie ma wartosci przez to ze nie mamy
                //tego w bazie danych, tablice i tak sa po referencji wiec mnie to mocno nie boli
                //in hindsight mozna bylo to inaczej zaprojektowac

                //FIXME mozna to zoptymalizowac, kod bedzie brzydszy ale bedzie szybszy tzn wywalenie creteDeviceDTO tutaj
                //Druga opcja to returnowanie obiektu {LedDTO,int[]}
                //Ewentualnie to wyjscie jest chyba najbardziej optymalne bo 1if co petle > 20 intow do przeiterowania co petle
                int[] addreses = device.get().getAddress();

                if(addreses.length !=1){
                    for(Integer address : addreses){
                        visitedAddresses.add(address);
                    }
                }
                currentStateDTOService.appendToArray(deviceDTOFactory.createDeviceDTO(device.get(),DMXDataArray,addreses), currentStateDTO);
            }

        }
        return currentStateDTO;
    }
}


