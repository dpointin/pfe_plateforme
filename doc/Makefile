all: graphes mvgraph tex mvdoc pseudoclean

tex: texRapport

graphes:
	cd src/Diagrammes_fin/; java -jar plantuml.jar *.uml; cd ../..

texRapport:
	cd src; pdflatex Rapport.tex;pdflatex Rapport.tex; cd ..

mvgraph:
	mv src/Diagrammes/*.png graph/

mvdoc:
	mv src/Rapport.pdf doc/PreRapportPFE.pdf

pseudoclean:
	rm -rf src/*.log src/*.aux src/*.toc src/*.out

clean: pseudoclean
	rm -rf graph/DiagrammeSequence*.png graph/DiagrammeDeCasDUtilisation.png src/Diagrammes/*.cmapx doc/*.pdf
