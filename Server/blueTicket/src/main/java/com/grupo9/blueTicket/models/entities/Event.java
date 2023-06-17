package com.grupo9.blueTicket.models.entities;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(exclude = "ticket")
@Entity
@Table(name = "Event")
public class Event {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(name = "title")
	private String title;

    //En la base es de tipo Date, aquí era String
    @Column(name = "date")
    private Date date;
    //Es de tipo Time en la base, aquí era Time
    @Column(name = "hour")
    private Time hour;

    @Column(name = "duration")
    private String duration;

    @Column(name = "sponsor")
    private String sponsor;

    @Column(name = "involved")
    private String involved;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = true)
    private User user;

	@Column(name = "main_image")
	private String image1;

    @Column(name = "secondary_image")
    private String image2;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category", nullable = true)
    private Category category;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_locality", nullable = true)
    private Locality locality;
    
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<Ticket> ticket; //La conexión con ticket

	public Event(String title, Date date, Time hour, String duration, String sponsor, String involved, User user,
			String image1, String image2, Category category, Locality locality) {
		super();
		this.title = title;
		this.date = date;
		this.hour = hour;
		this.duration = duration;
		this.sponsor = sponsor;
		this.involved = involved;
		this.user = user;
		this.image1 = image1;
		this.image2 = image2;
		this.category = category;
		this.locality = locality;
	}
    

	
    
}
