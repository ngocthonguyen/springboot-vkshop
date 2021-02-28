package net.ntn.training.vkshop.business.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sexe")
    private Integer sexe;

    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "description")
    private String description;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

    @Column(name = "adresse_facturation")
    private String adresseFacturation;

    @Column(name = "adresse_livraison")
    private String adresseLivraison;

    @Column(name = "tel")
    private String tel;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSexe() {
        return sexe;
    }

    public Customer sexe(Integer sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Integer sexe) {
        this.sexe = sexe;
    }

    public String getPseudo() {
        return pseudo;
    }

    public Customer pseudo(String pseudo) {
        this.pseudo = pseudo;
        return this;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getFirstname() {
        return firstname;
    }

    public Customer firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Customer lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDescription() {
        return description;
    }

    public Customer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public Customer dateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
        return this;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getAdresseFacturation() {
        return adresseFacturation;
    }

    public Customer adresseFacturation(String adresseFacturation) {
        this.adresseFacturation = adresseFacturation;
        return this;
    }

    public void setAdresseFacturation(String adresseFacturation) {
        this.adresseFacturation = adresseFacturation;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public Customer adresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
        return this;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public String getTel() {
        return tel;
    }

    public Customer tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Customer password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", sexe=" + getSexe() +
            ", pseudo='" + getPseudo() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateBirth='" + getDateBirth() + "'" +
            ", adresseFacturation='" + getAdresseFacturation() + "'" +
            ", adresseLivraison='" + getAdresseLivraison() + "'" +
            ", tel='" + getTel() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
