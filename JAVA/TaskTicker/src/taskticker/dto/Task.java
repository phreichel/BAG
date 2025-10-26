//*****************************************************************************
package taskticker.dto;
//*****************************************************************************

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//*****************************************************************************

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Task {

	private String ident;
	private Worker worker;
	private Assignment assignment;
	private LocalDateTime start;
	private LocalDateTime stop;
	
}
//*****************************************************************************
