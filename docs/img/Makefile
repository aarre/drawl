# LaTeX Makefile v0.33 -- LaTeX only


FIGURE=rhombus  # set the path to your TeX file here
SHELL=/bin/bash   # for the while loop below

 ## Compile paper


all:	rhombus.svg triangle.svg

.PRECIOUS: %.pdf

%.pdf:	%.tex
	rubber --pdf $<

%.svg:	%.pdf
	pdf2svg $< $@

clean:  ## Clean output files
	rubber --clean rhombus.tex

watch:  ## Recompile on updates to the source file
    #@while [ 1 ]; do; inotifywait $(PAPER); sleep 0.01; make all; done
    # for Bash users, replace the while loop with the following
	@while true; do; inotifywait $(FIGURE); sleep 0.01; make all; done
