import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;


import com.udacity.gradle.builditbigger.EndpointsAsyncTask;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by ridsys-001 on 10/10/17.
 */
@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest {

    private static final String TAG = AsyncTaskTest.class.getSimpleName();

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        String result = null;
        EndpointsAsyncTask task = new EndpointsAsyncTask(InstrumentationRegistry.getTargetContext(),
                null);
        task.execute();
        try {
            result = task.get();
            Log.i(TAG, "The result is " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(result);
    }

}
