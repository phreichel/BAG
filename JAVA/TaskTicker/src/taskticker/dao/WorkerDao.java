//*****************************************************************************
package taskticker.dao;
//*****************************************************************************

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//*****************************************************************************

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "Worker")
public class WorkerDao {

	//=========================================================================

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	//=========================================================================

	@Column(nullable = false, unique = true)
	private String ident;
	
	//=========================================================================
	
	private String name;

	//=========================================================================

}
//*****************************************************************************
