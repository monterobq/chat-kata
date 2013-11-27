package org.ejmc.android.simplechat;

/**
 * Created with IntelliJ IDEA.
 * User: montero
 * Date: 26/11/13
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {

    @Test
    public void shouldHaveProperAppName() throws Exception{
        String appName = new LoginActivity().getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("SimpleChat"));
    }
}
