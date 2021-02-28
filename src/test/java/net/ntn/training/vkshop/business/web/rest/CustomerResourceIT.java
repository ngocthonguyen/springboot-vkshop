package net.ntn.training.vkshop.business.web.rest;

import net.ntn.training.vkshop.business.VkshopApp;
import net.ntn.training.vkshop.business.domain.Customer;
import net.ntn.training.vkshop.business.repository.CustomerRepository;
import net.ntn.training.vkshop.business.service.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@SpringBootTest(classes = VkshopApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerResourceIT {

    private static final Integer DEFAULT_SEXE = 1;
    private static final Integer UPDATED_SEXE = 2;

    private static final String DEFAULT_PSEUDO = "AAAAAAAAAA";
    private static final String UPDATED_PSEUDO = "BBBBBBBBBB";

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ADRESSE_FACTURATION = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_FACTURATION = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_LIVRAISON = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_LIVRAISON = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerMockMvc;

    private Customer customer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .sexe(DEFAULT_SEXE)
            .pseudo(DEFAULT_PSEUDO)
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .description(DEFAULT_DESCRIPTION)
            .dateBirth(DEFAULT_DATE_BIRTH)
            .adresseFacturation(DEFAULT_ADRESSE_FACTURATION)
            .adresseLivraison(DEFAULT_ADRESSE_LIVRAISON)
            .tel(DEFAULT_TEL)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD);
        return customer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .sexe(UPDATED_SEXE)
            .pseudo(UPDATED_PSEUDO)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .description(UPDATED_DESCRIPTION)
            .dateBirth(UPDATED_DATE_BIRTH)
            .adresseFacturation(UPDATED_ADRESSE_FACTURATION)
            .adresseLivraison(UPDATED_ADRESSE_LIVRAISON)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();
        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testCustomer.getPseudo()).isEqualTo(DEFAULT_PSEUDO);
        assertThat(testCustomer.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testCustomer.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testCustomer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustomer.getDateBirth()).isEqualTo(DEFAULT_DATE_BIRTH);
        assertThat(testCustomer.getAdresseFacturation()).isEqualTo(DEFAULT_ADRESSE_FACTURATION);
        assertThat(testCustomer.getAdresseLivraison()).isEqualTo(DEFAULT_ADRESSE_LIVRAISON);
        assertThat(testCustomer.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomer.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE)))
            .andExpect(jsonPath("$.[*].pseudo").value(hasItem(DEFAULT_PSEUDO)))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateBirth").value(hasItem(DEFAULT_DATE_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].adresseFacturation").value(hasItem(DEFAULT_ADRESSE_FACTURATION)))
            .andExpect(jsonPath("$.[*].adresseLivraison").value(hasItem(DEFAULT_ADRESSE_LIVRAISON)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }
    
    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE))
            .andExpect(jsonPath("$.pseudo").value(DEFAULT_PSEUDO))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateBirth").value(DEFAULT_DATE_BIRTH.toString()))
            .andExpect(jsonPath("$.adresseFacturation").value(DEFAULT_ADRESSE_FACTURATION))
            .andExpect(jsonPath("$.adresseLivraison").value(DEFAULT_ADRESSE_LIVRAISON))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD));
    }
    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .sexe(UPDATED_SEXE)
            .pseudo(UPDATED_PSEUDO)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .description(UPDATED_DESCRIPTION)
            .dateBirth(UPDATED_DATE_BIRTH)
            .adresseFacturation(UPDATED_ADRESSE_FACTURATION)
            .adresseLivraison(UPDATED_ADRESSE_LIVRAISON)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomer)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testCustomer.getPseudo()).isEqualTo(UPDATED_PSEUDO);
        assertThat(testCustomer.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testCustomer.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testCustomer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomer.getDateBirth()).isEqualTo(UPDATED_DATE_BIRTH);
        assertThat(testCustomer.getAdresseFacturation()).isEqualTo(UPDATED_ADRESSE_FACTURATION);
        assertThat(testCustomer.getAdresseLivraison()).isEqualTo(UPDATED_ADRESSE_LIVRAISON);
        assertThat(testCustomer.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomer.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
