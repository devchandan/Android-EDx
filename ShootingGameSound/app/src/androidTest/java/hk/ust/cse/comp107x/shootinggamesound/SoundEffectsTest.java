package hk.ust.cse.comp107x.shootinggamesound;

/**
 * Created by muppala on 7/7/15.
 */

import android.content.Context;
import android.test.suitebuilder.annotation.SmallTest;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * JUnit4 unit tests for the Score class logic.
 */
@RunWith(MockitoJUnitRunner.class)
@SmallTest
public class SoundEffectsTest {

    @Mock
    Context mMockContext;

    @Before
    public void setUp() {
        SoundEffects.INSTANCE.setContext(mMockContext);
    }

    @Test
    public void getNumSounds () {
        int numSounds = SoundEffects.INSTANCE.getNumSounds();
        assertThat(numSounds, is(equalTo(1)));
    }

}
