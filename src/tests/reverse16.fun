x10a # Save the init value.
,s>A-a< # Get the loop started.

[
	,s # Push the character onto the stack.
	>A-a< # Decrease
	A # Get ready for checking.
]  

[ # Return the text, in reverse order.
	S.
]

x00q # Exit
