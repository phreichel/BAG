//*****************************************************************************
package homeapp.db;
//*****************************************************************************

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//*****************************************************************************
@Entity(name = "Link")
@Table(name = "link")
public class Link {

	//=========================================================================
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	//=========================================================================

	//=========================================================================
	@ManyToOne
	@JoinColumn(name = "source_node")
	public Node source;
	//=========================================================================

	//=========================================================================
	@ManyToOne
	@JoinColumn(name = "target_node")
	public Node target;	
	//=========================================================================

	//=========================================================================
	@Column(name = "kind", unique = false, length = 255, nullable = true)
	public String kind;
	//=========================================================================

}
//*****************************************************************************
