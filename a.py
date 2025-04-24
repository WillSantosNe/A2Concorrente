nome = input("olá, qual é seu nome?: ")
input(f"olá {nome}, é um prazer te conhecer, posso fazer conta de multiplicação para você! É  sé apertar ENTER para começar! ")
numero1 = int(input(f"fale o primeiro valor: "))
numero2 = int(input(f"Ok, agora, fale o valor que quer multiplicar com {numero1}?: "))
resultado = (numero1)*(numero2)
print(f"Então {nome}, o valor de ({numero1}) multiplicado por ({numero2}) ({numero1}X{numero2}) é ({resultado})")


