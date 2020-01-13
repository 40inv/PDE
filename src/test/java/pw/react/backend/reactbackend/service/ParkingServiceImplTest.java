package pw.react.backend.reactbackend.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pw.react.backend.reactbackend.dao.ParkingRepository;
import pw.react.backend.reactbackend.model.Parking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ParkingServiceImplTest {


    @Mock
    private ParkingRepository repository;
    @Spy
    @InjectMocks
    private ParkingServiceImpl parkingService;

    @Test
    public void givenNotExistingId_whenUpdateParking_theReturnCompanyEMPTY() {
        when(repository.existsById(anyLong())).thenReturn(false);

        Parking actual = parkingService.updateParking(1L, new Parking());

        assertThat(actual).isEqualTo(Parking.EMPTY);
        verify(repository, times(1)).existsById(eq(1L));
        verify(repository, times(0)).save(any(Parking.class));
    }

    @Test
    public void givenExistingId_whenUpdateParking_theReturnUpdatedParking() {
        Parking updatedParking = new Parking();
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.save(any(Parking.class))).thenReturn(updatedParking);

        Parking actual = parkingService.updateParking(1L, updatedParking);

        assertThat(actual).isEqualTo(updatedParking);
        verify(repository, times(1)).existsById(eq(1L));
        verify(repository, times(1)).save(eq(updatedParking));
    }


}
