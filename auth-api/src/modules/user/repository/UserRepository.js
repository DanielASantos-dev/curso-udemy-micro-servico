import e from "express";
import { where } from "sequelize";
import User from "../model/User.js";

class UserRepository {

    async findById(id) {
        try {
            return await User.findById({ where: { id } });
        } catch (error) {
            console.error(error.message);
        }
    }

    async findByEmail(email) {
        try {
            return await User.findOne({ where: { email } });
        } catch (err) {
            console.err(err.message)
            return null;
        }
    }
}

export default new UserRepository();