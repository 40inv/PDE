package pw.react.backend.reactbackend.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pw.react.backend.reactbackend.dao.BookingRepository;
import pw.react.backend.reactbackend.model.Booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceImplTest {

    @Mock
    private BookingRepository repository;
    @Spy
    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    public void givenNotExistingId_whenUpdateBooking_theReturnCompanyEMPTY() {
        when(repository.existsById(anyLong())).thenReturn(false);

        Booking actual = bookingService.updateBooking(1L, new Booking());

        assertThat(actual).isEqualTo(Booking.EMPTY);
        verify(repository, times(1)).existsById(eq(1L));
        verify(repository, times(0)).save(any(Booking.class));
    }

    @Test
    public void givenExistingId_whenUpdateBooking_theReturnUpdatedBooking() {
        Booking updatedBooking = new Booking();
        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.save(any(Booking.class))).thenReturn(updatedBooking);

        Booking actual = bookingService.updateBooking(1L, updatedBooking);

        assertThat(actual).isEqualTo(updatedBooking);
        verify(repository, times(1)).existsById(eq(1L));
        verify(repository, times(1)).save(eq(updatedBooking));
    }

}
