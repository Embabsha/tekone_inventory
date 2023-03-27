
import com.example.demo.module.Customer;
import com.example.demo.module.CustomersCollection;
import org.junit.jupiter.api.Test;

public class CustomerListControllerTest {

    private CustomersCollection ac ;
    private Customer ad;

    @Test
    public void loadCustomer(){
        ac = new CustomersCollection();
        ac.getAllCustomers();

    }
}
