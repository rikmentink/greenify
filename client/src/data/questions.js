const questions = [
    {
        question: "facilitatingFactor",
        options: [
            {
                name: "TOTALLY_DISAGREE",
                displayName: "Totally disagree",
                value: 0
            },
            {
                name: "DISAGREE",
                displayName: "Disagree",
                value: 1
            },
            {
                name: "NEUTRAL",
                displayName: "Neutral",
                value: 2
            },
            {
                name: "AGREE",
                displayName: "Agree",
                value: 3
            },
            {
                name: "TOTALLY_AGREE",
                displayName: "Totally agree",
                value: 4
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
                value: 3
            },
            {
                name: "LITTLE_PRIORITY",
                displayName: "Little priority",
                value: 2
            },
            {
                name: "PRIORITY",
                displayName: "Priority",
                value: 1
            },
            {
                name: "TOP_PRIORITY",
                displayName: "Top priority",
                value: 0
            }
        ]
    }
];

export default { questions };