package com.grupo9.blueTicket.models.entities;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo9.blueTicket.models.entities.Token;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(exclude = { "transfer_user_issuer", "transfer_user_receptor", "sale"})
@Entity
@Table(name = "user")

public class User implements UserDetails {
	 @Id
	 @Column(name = "id")
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private UUID id;
	 
	 @Column(name = "name")
	 private String username;

	 @Column(name = "email")
	 private String email;

	 @Column(name = "password")
	 private String password;
	 
	 @Column(name = "active", insertable = false)
	 private Boolean active;
	 /*
	 //Creando la conexión con el event
	 @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	 private List<Event> event;
	 */
	 //FK de role
	 @ManyToOne(fetch = FetchType.EAGER)
	 @JoinColumn(name = "id_role", insertable = false)
	 private Role role;
	 //Creo que falta establecer la conexión con la tabla transfer y sale
	 
	 //Conexión con transfer
	 @OneToMany(mappedBy = "user_issuer", fetch = FetchType.LAZY)
	 private List<Transfer> transfer_user_issuer;
	 @OneToMany(mappedBy = "user_receptor", fetch = FetchType.LAZY)
	 private List<Transfer> transfer_user_receptor;
	 
	 //Conexión con Sale
	 @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	 private List<Sale> sale;
	 //Esto creo que tendría que ser Access
	 @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
		@JsonIgnore
		private List<Token> tokens;
		
		private static final long serialVersionUID = 1460435087476558985L;


		public User(String username, String email, String password, Boolean active) {
			super();
			this.username = username;
			this.email = email;
			this.password = password;
			this.active = active;
			
			
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
		}
		//getUsername is already overridden
		@Override
		public boolean isAccountNonExpired() {
		return false;
		}
		@Override
		public boolean isAccountNonLocked() {
		return false;
		}
		@Override
		public boolean isCredentialsNonExpired() {
		return false;
		}
		@Override
		public boolean isEnabled() {
		return this.active;
		}

        public Event getEvent() {
            return null;
        }

}
