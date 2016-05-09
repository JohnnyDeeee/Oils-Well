# Oils-Well
School Project 2015/2016 MBO GameDevelopment

## Index
[Version History](https://github.com/JohnnyDeeee/Oils-Well#version-history)</BR>
[To-Do](https://github.com/JohnnyDeeee/Oils-Well#to-do)</BR>
[Done](https://github.com/JohnnyDeeee/Oils-Well#done)</BR>
</BR>
</BR>
</BR>
</BR>
</BR>
</BR>
</BR>
</BR>
</BR>
</BR>
</BR>
</BR>
</BR>
</BR>

## Version History
**V0.0.5 (current version)**
    
    Added:
	- Travel back functions
	- Rope that player is attached to
	- New "candy" like main menu theme
	
	Removed:
	- Old main menu theme
	
	Fixed:
	- N/A
    
**V0.0.4**
	
    Added:
	- New collision detection (using object layers in Tiled)
	
	Removed:
	- Old tile set style (mountain)
	- Old collision detection (getting cells)
	
	Fixed:
	- N/A
    
**V0.0.3**
	
    Added:
	- Collision Detection
	- Player
		- Movement
		- Animations
	
	Removed:
	- N/A
		
	Fixed:
	- N/A

**V0.0.2**
	
    Added:
	- Improvement to animating main menu
	- Music to main menu
	- Loading screen
	- Code cleanup
	
	Removed:
	- Standard buttons (replaced them with labels)
	
	Fixed:
	- N/A

**V0.0.1**
	
    Added:
	- Splash screen
		- Animations
	- Main menu screen
		- Animations
	- Play screen
	- Options screen
	
	Removed:
	- N/A
	
	Fixed:
	- N/A

## TO-DO
**Main screen**	
- Add labels instead of buttons
- Add click sound
    - Add hover sound

**Loading screen**	
- Add assetManager
    - show progress bar for loading (WIP)
    - can click on play trough loading screen???

**Play screen**	
- Create a tilemap [WIP]
- Create player respawn [WIP]
    - create decrease in live and points
- Create AI [WIP]
- Create HUD
    - Create lives
    - Position HUD in tiled

**Highscore Screen**	
- ...

## DONE
**Main screen**	
- Create the screen
- Add labels instead of buttons
    - Add color effect
    - Add functionality

**Loading screen**	
- Add assetManager

**Play screen**	
- Create a player
    - Create animations
    - Create basic collision Detection (overlapping)
    - Create basic movement
    - Path creation for player (so player can travel back the same path)
        - create visible line (rope) that he player is attached to
        - create going back animations
        - Create player respawn [WIP]
- Create AI [WIP]
- Create the screen
    - Create camera
- Show version number
- Add background music
- Create objects
    - Create "power" object
    - Create "point" object

**Highscore Screen**	
- Create top 5 scores
    - Read from json file

**EndGame Screen**	
- Save points and username to text file
    - Create "save profile" screen