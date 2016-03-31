all: clean doc/Beamer.pdf pseudoclean 

graphes:
	cd src/diagrams/; java -jar plantuml.jar *.uml; cd ../..

doc/Beamer.pdf: src/Beamer.tex
	cd src;pdflatex Beamer.tex && pdflatex Beamer.tex && mv Beamer.pdf ../doc; cd ..   
    
mvgraph:
	mv src/diagrams/*.png src/images/
	
cleangraph:
	rm -rf src/graph/*.png	

pseudoclean:
	rm -rf src/*.log src/*.aux src/*.toc src/*.out src/*.nav src/*.snm src/*.vrb

clean: pseudoclean cleangraph
	rm -rf doc/*.pdf
