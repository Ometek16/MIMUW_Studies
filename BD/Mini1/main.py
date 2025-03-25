import csv

filename = "Publikacje.csv"

with open(filename, "r") as file:
    data = list(csv.reader(file))

with open("output.txt", "w") as output_file:
    output_file.write("CREATE TABLE Publikacje (\n")
    output_file.write("\tid INT,\n")
    output_file.write("\ttytul VARCHAR(420),\n")
    output_file.write("\trok INT,\n")
    output_file.write("\tautorzy INT,\n")
    output_file.write("\tpunkty INT );\n\n")
    
    output_file.write("INSERT INTO Publikacje VALUES\n")
    
    for idx, line in enumerate(data[1:]):
        line = [int(line[0]), line[1].replace("'", "''"), int(line[2]), int(line[3]), int(line[4])]
        output_file.write("\t" + str(tuple(line)) + (",\n" if idx != len(data) - 2 else ";\n\n"))     
        
    output_file.write("DROP TABLE Publikacje;\n")