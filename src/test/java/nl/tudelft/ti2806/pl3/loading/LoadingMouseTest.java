package nl.tudelft.ti2806.pl3.loading;

import nl.tudelft.ti2806.pl3.Application;
import nl.tudelft.ti2806.pl3.LoadingObservable;
import nl.tudelft.ti2806.pl3.loading.LoadingMouse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.Cursor;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for the LoadingMouse
 * Created by Kasper on 10-6-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoadingMouseTest {

    @Mock
    Application application;
    @Mock
    LoadingObservable loadingObservable;


    @Test
    public void testUpdateFalse() {
        LoadingMouse loadingMouse = new LoadingMouse(application);
        loadingMouse.update(loadingObservable, false);

        verify(application, times(1)).setCursor(Cursor.getDefaultCursor());
    }

    @Test
    public void testUpdateTrue() {
        LoadingMouse loadingMouse = new LoadingMouse(application);
        loadingMouse.update(loadingObservable, true);

        verify(application, times(1)).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        loadingMouse.update(loadingObservable, false);
        verify(application, times(1)).setCursor(Cursor.getDefaultCursor());

    }


}
