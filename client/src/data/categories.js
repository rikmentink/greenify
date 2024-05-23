const categories = {
    name: "Dummy Survey",
    description: "This is a dummy survey description.",
    phase: "Dummy Phase",
    categories: [
        {
            categoryId: 1,
            name: "Category 1",
            description: "Description for Category 1",
            questions: [
                { id: 1, questionId: 1 },
                { id: 2, questionId: 1 },
                { id: 3, questionId: 1 }
            ]
        },
        {
            categoryId: 2,
            name: "Category 2",
            description: "Description for Category 2",
            questions: [
                { id: 1, questionId: 2 },
                { id: 2, questionId: 2 },
                { id: 3, questionId: 2 }
            ]
        },
        {
            categoryId: 3,
            name: "Category 3",
            description: "Description for Category 3",
            questions: [
                { id: 1, questionId: 3 },
                { id: 2, questionId: 3 },
                { id: 3, questionId: 3 }
            ]
        }
    ]
}

export default { categories };