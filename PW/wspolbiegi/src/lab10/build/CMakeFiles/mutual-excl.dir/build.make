# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.31

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Disable VCS-based implicit rules.
% : %,v

# Disable VCS-based implicit rules.
% : RCS/%

# Disable VCS-based implicit rules.
% : RCS/%,v

# Disable VCS-based implicit rules.
% : SCCS/s.%

# Disable VCS-based implicit rules.
% : s.%

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /opt/homebrew/bin/cmake

# The command to remove a file.
RM = /opt/homebrew/bin/cmake -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10/build

# Include any dependencies generated for this target.
include CMakeFiles/mutual-excl.dir/depend.make
# Include any dependencies generated by the compiler for this target.
include CMakeFiles/mutual-excl.dir/compiler_depend.make

# Include the progress variables for this target.
include CMakeFiles/mutual-excl.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/mutual-excl.dir/flags.make

CMakeFiles/mutual-excl.dir/codegen:
.PHONY : CMakeFiles/mutual-excl.dir/codegen

CMakeFiles/mutual-excl.dir/mutual-excl.c.o: CMakeFiles/mutual-excl.dir/flags.make
CMakeFiles/mutual-excl.dir/mutual-excl.c.o: /Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10/mutual-excl.c
CMakeFiles/mutual-excl.dir/mutual-excl.c.o: CMakeFiles/mutual-excl.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --progress-dir=/Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/mutual-excl.dir/mutual-excl.c.o"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -MD -MT CMakeFiles/mutual-excl.dir/mutual-excl.c.o -MF CMakeFiles/mutual-excl.dir/mutual-excl.c.o.d -o CMakeFiles/mutual-excl.dir/mutual-excl.c.o -c /Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10/mutual-excl.c

CMakeFiles/mutual-excl.dir/mutual-excl.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Preprocessing C source to CMakeFiles/mutual-excl.dir/mutual-excl.c.i"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10/mutual-excl.c > CMakeFiles/mutual-excl.dir/mutual-excl.c.i

CMakeFiles/mutual-excl.dir/mutual-excl.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green "Compiling C source to assembly CMakeFiles/mutual-excl.dir/mutual-excl.c.s"
	/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10/mutual-excl.c -o CMakeFiles/mutual-excl.dir/mutual-excl.c.s

# Object files for target mutual-excl
mutual__excl_OBJECTS = \
"CMakeFiles/mutual-excl.dir/mutual-excl.c.o"

# External object files for target mutual-excl
mutual__excl_EXTERNAL_OBJECTS =

mutual-excl: CMakeFiles/mutual-excl.dir/mutual-excl.c.o
mutual-excl: CMakeFiles/mutual-excl.dir/build.make
mutual-excl: CMakeFiles/mutual-excl.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color "--switch=$(COLOR)" --green --bold --progress-dir=/Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10/build/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking C executable mutual-excl"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/mutual-excl.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/mutual-excl.dir/build: mutual-excl
.PHONY : CMakeFiles/mutual-excl.dir/build

CMakeFiles/mutual-excl.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/mutual-excl.dir/cmake_clean.cmake
.PHONY : CMakeFiles/mutual-excl.dir/clean

CMakeFiles/mutual-excl.dir/depend:
	cd /Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10 /Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10 /Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10/build /Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10/build /Users/kubaornatek/Documents/Studies/UW_3/PW/wspolbiegi/src/lab10/build/CMakeFiles/mutual-excl.dir/DependInfo.cmake "--color=$(COLOR)"
.PHONY : CMakeFiles/mutual-excl.dir/depend

