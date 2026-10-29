def check_braces(filename):
    with open(filename, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    stack = []
    for idx, line in enumerate(lines, 1):
        for char in line:
            if char == '{':
                stack.append(idx)
            elif char == '}':
                if not stack:
                    print(f"Extra closing brace at line {idx}")
                else:
                    stack.pop()
    
    if stack:
        print(f"Unclosed braces opened at lines: {stack}")
    else:
        print("All braces balanced!")

check_braces("app/src/main/java/com/example/ui/FatouCalendarScreen.kt")
