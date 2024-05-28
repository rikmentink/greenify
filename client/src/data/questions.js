const questions = [
    {
        question: "facilitatingFactor",
        options: [
            {
                name: "TOTALLY_DISAGREE",
                displayName: "Totally disagree",
                value: 1
            },
            {
                name: "DISAGREE",
                displayName: "Disagree",
                value: 2
            },
            {
                name: "NEUTRAL",
                displayName: "Neutral",
                value: 3
            },
            {
                name: "AGREE",
                displayName: "Agree",
                value: 4
            },
            {
                name: "TOTALLY_AGREE",
                displayName: "Totally agree",
                value: 5
            },
            // {
            //     name: "I_DONT_KNOW",
            //     displayName: "Don't know",
            //     value: 0
            // }
        ]
    },
    {
        question: "priority",
        options: [
            {
                name: "NO_PRIORITY",
                displayName: "No priority",
                value: 1
            },
            {
                name: "LITTLE_PRIORITY",
                displayName: "Little priority",
                value: 2
            },
            {
                name: "PRIORITY",
                displayName: "Priority",
                value: 3
            },
            {
                name: "TOP_PRIORITY",
                displayName: "Top priority",
                value: 4
            }
        ]
    }
];

export default { questions };