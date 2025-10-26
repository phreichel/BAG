//*****************************************************************************
package taskticker.dto;
//*****************************************************************************

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//*****************************************************************************

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Assignment {

	//=========================================================================
	private String ident;
	private String status;
	private LocalDateTime earlyStart;
	private LocalDateTime lateStart;
	private LocalDateTime earlyStop;
	private LocalDateTime lateStop;
	private Assignment compilation;
	private List<Assignment> breakdown;
	private List<Task> tasks;
	//=========================================================================

}
//*****************************************************************************
