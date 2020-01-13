package pw.react.backend.reactbackend.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pw.react.backend.reactbackend.dao.ParkingSpotRepository;
import pw.react.backend.reactbackend.model.ParkingSpot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ParkingSpotServiceImplTest {

    @Mock
    private ParkingSpotRepository repository;
    @Spy
    @InjectMocks
    private ParkingSpotServiceImpl parkingSpotService;

    @Test
    public void givenNotExistingId_whenUpdateParkingSpot_theReturnCompanyEMPTY() {
        when(repository.existsById(anyLong())).thenReturn(false);

        ParkingSpot actual = parkingSpotService.updateParkingSpot(1L, new ParkingSpot());

        assertThat(actual).isEqualTo(ParkingSpot.EMPTY);
        verify(repository, times(1)).existsById(eq(1L));
        verify(repository, times(0)).save(any(ParkingSpot.class));
    }

    @Test
    public void givenExistingId_whenUpdateParkingSpot_theReturnUpdatedParkingSpot() {
        ParkingSpot updatedParkingSpot = new ParkingSpot();
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.save(any(ParkingSpot.class))).thenReturn(updatedParkingSpot);

        ParkingSpot actual = parkingSpotService.updateParkingSpot(1L, updatedParkingSpot);

        assertThat(actual).isEqualTo(updatedParkingSpot);
        verify(repository, times(1)).existsById(eq(1L));
        verify(repository, times(1)).save(eq(updatedParkingSpot));
    }


}