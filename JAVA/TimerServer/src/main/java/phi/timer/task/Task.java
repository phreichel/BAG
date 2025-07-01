package phi.timer.task;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Task")
@Table(name = "TASK")
public class Task {

	@Getter
	@Setter
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Getter
	@Setter
	@Column(name = "IDENT", length = 256, nullable = false)
	private String ident;

	@Getter
	@Setter
	@Column(name = "SUBJECT")
	private String subject;
	
}
