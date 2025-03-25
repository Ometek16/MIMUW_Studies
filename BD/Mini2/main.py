import csv


with open("Autorstwo.csv", "r") as file:
    autorstwo = list(csv.reader(file))

with open("Autorzy.csv", "r") as file:
    autorzy = list(csv.reader(file))

with open("Prace.csv", "r") as file:
    prace = list(csv.reader(file))

with open("output.txt", "w") as output_file:
    output_file.write("CREATE TABLE Autorstwo (\n")
    output_file.write("\tid INT PRIMARY KEY,\n")
    output_file.write("\tpraca INT,\n")
    output_file.write("\tautor VARCHAR(69) );\n\n")
    
    output_file.write("INSERT INTO Autorstwo VALUES\n")
    
    for idx, line in enumerate(autorstwo[1:]):
        line = [int(idx), int(line[0]), line[1]]
        output_file.write("\t" + str(tuple(line)) + (",\n" if idx != len(autorstwo) - 2 else ";\n\n"))     
        
    output_file.write("CREATE TABLE Autorzy (\n")
    output_file.write("\tid VARCHAR(69) UNIQUE,\n")
    output_file.write("\tryzyko INT,\n")
    output_file.write("\tsloty NUMERIC(3,2) );\n\n")
    
    output_file.write("INSERT INTO Autorzy VALUES\n")
    
    for idx, line in enumerate(autorzy[1:]):
        try:
            num = int(line[2])
        except:
            num = float(line[2].replace(',', '.'))
        line = [line[0], int(line[1]), num]
        output_file.write("\t" + str(tuple(line)) + (",\n" if idx != len(autorzy) - 2 else ";\n\n"))  
        
    output_file.write("CREATE TABLE Prace (\n")
    output_file.write("\tid INT PRIMARY KEY,\n")
    output_file.write("\ttytul VARCHAR(420),\n")
    output_file.write("\trok INT,\n")
    output_file.write("\tautorzy INT,\n")
    output_file.write("\tpunkty INT );\n\n")
    
    output_file.write("INSERT INTO Prace VALUES\n")
        
    for idx, line in enumerate(prace[1:]):
        line = [int(line[0]), line[1].replace('"', '').replace("'", "''"), int(line[2]), int(line[3]), int(line[4])]
        output_file.write("\t" + str(tuple(line)) + (",\n" if idx != len(prace) - 2 else ";\n\n"))     
        
        
    output_file.write("DROP TABLE Autorstwo;\n")
    output_file.write("DROP TABLE Autorzy;\n")
    output_file.write("DROP TABLE Prace;\n")
