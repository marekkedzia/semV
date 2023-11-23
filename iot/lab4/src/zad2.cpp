// #include <Arduino.h>

// #define RED_BUTTON 2
// #define GREEN_BUTTON 4

// #define LED_RED 6
// #define LED_GREEN 5
// #define LED_BLUE 3

// void initButtons()
// {
// }

// void setup()
// {
//     pinMode(RED_BUTTON, INPUT_PULLUP);
//     pinMode(GREEN_BUTTON, INPUT_PULLUP);
//     Serial.begin(9600);
//     while (!Serial)
//     {
//     }
// }

// String getCommand()
// {
//     String command = Serial.readStringUntil('\n');
//     command.trim();
//     command.toLowerCase();
//     return command;
// }

// void loop()
// {
//     if (Serial.available() > 0)
//     {
//         String command = getCommand();

//         char firstLetter = command.charAt(0);
//         if (firstLetter == 'r')
//             analogWrite(LED_RED, command.substring(1).toInt());
//         else if (firstLetter == 'g')
//             analogWrite(LED_GREEN, command.substring(1).toInt());
//         else if (firstLetter == 'b')
//             analogWrite(LED_BLUE, command.substring(1).toInt());
//         else
//             Serial.println("Wrong command");
//     }
// }